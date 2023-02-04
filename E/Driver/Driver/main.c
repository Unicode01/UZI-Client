#include "Driver.h"
UNICODE_STRING NAME, SYM_NAME;
PDEVICE_OBJECT PDriverObject;
ULONG64 ProcessPid;
ULONG64 UNIQUEPROCESSID_OFFSET;

ULONG64 Get64bitValue(PVOID p)
{
	//判断写入该地址是否会发生页面错误
	if (MmIsAddressValid(p) == FALSE)
		return 0;
	return *(PULONG64)p;
}

VOID Set64bitValue(PVOID p, ULONG64 v)
{
	KIRQL OldIrql;
	//判断写入该地址是否会发生页面错误
	if (MmIsAddressValid(p) == FALSE)
		return;
	OldIrql = KeRaiseIrqlToDpcLevel();
	*(PULONG64)p = v;
	KeLowerIrql(OldIrql);
}

NTSTATUS DriverUnload(PDRIVER_OBJECT pDriverObj)
{
	NTSTATUS status = STATUS_SUCCESS;

	//删除符号链接
	IoDeleteSymbolicLink(&SYM_NAME);

	//删除通讯对象设备
	IoDeleteDevice(PDriverObject);

	return status;
}

NTSTATUS DispatchCreate(PDEVICE_OBJECT pDriverObj, PIRP pIrp)
{
	UNREFERENCED_PARAMETER(pDriverObj);

	pIrp->IoStatus.Status = STATUS_SUCCESS;
	pIrp->IoStatus.Information = 0;

	IoCompleteRequest(pIrp, IO_NO_INCREMENT);

	return STATUS_SUCCESS;
}

VOID SetProcess(HANDLE ProcessId,HANDLE SETULONGPID)
{
	PEPROCESS eProcess;

	//取进程EPROCESS结构指针
	PsLookupProcessByProcessId(ProcessId, &eProcess);

	ProcessPid = Get64bitValue((PULONG64)((ULONG64)eProcess + UNIQUEPROCESSID_OFFSET)); //保存原来的进程PID  

	Set64bitValue((PULONG64)((ULONG64)eProcess + UNIQUEPROCESSID_OFFSET), (ULONG64)SETULONGPID);//写入新的PID值

	ObDereferenceObject(eProcess);
}

VOID ReSetProcess(HANDLE ProcessId)
{
	PEPROCESS eProcess;
	if (ProcessPid != 0)
	{
		//取进程EPROCESS结构指针
		PsLookupProcessByProcessId(ProcessId, &eProcess);

		Set64bitValue((PULONG64)((ULONG64)eProcess + UNIQUEPROCESSID_OFFSET), ProcessPid);//写入原来的PID值

		ObDereferenceObject(eProcess);
	}
}


NTSTATUS DispatchIoctl(PDEVICE_OBJECT pDriverObj, PIRP pIrp)
{
	UNREFERENCED_PARAMETER(pDriverObj);
	NTSTATUS Status = STATUS_SUCCESS;
	PDataStruct pInputData;
	PIO_STACK_LOCATION  IoStackLocation = NULL;
	ULONG OutputDataLength = 0, IoControlCode = 0;
	pInputData = pIrp->AssociatedIrp.SystemBuffer;
	IoStackLocation = IoGetCurrentIrpStackLocation(pIrp);
	IoControlCode = IoStackLocation->Parameters.DeviceIoControl.IoControlCode;
	OutputDataLength = IoStackLocation->Parameters.DeviceIoControl.OutputBufferLength;

	switch (IoControlCode)
	{
	case IOCTL_IO_SetPROTECT:
	{
		//调用修改PID
		SetProcess(pInputData->ProcessPid, pInputData->SetLongPid);

		Status = STATUS_SUCCESS;
		break;
	}
	case IOCTL_IO_ReSetPROTECT:
	{
		//调用还原PID
		ReSetProcess(pInputData->ProcessPid);

		Status = STATUS_SUCCESS;
		break;
	}
	default:
		Status = STATUS_UNSUCCESSFUL;
		break;
	}
	if (Status == STATUS_SUCCESS)
		pIrp->IoStatus.Information = OutputDataLength;
	else
		pIrp->IoStatus.Information = 0;
	pIrp->IoStatus.Status = Status;
	IoCompleteRequest(pIrp, IO_NO_INCREMENT);
	return Status;
}

NTSTATUS DriverEntry(PDRIVER_OBJECT pDriverObj, PUNICODE_STRING pRegistryString)
{
	NTSTATUS Status = STATUS_SUCCESS;
	PBYTE apiaddr = NULL;
	UNICODE_STRING	routineName;
	UNREFERENCED_PARAMETER(pRegistryString);

	pDriverObj->MajorFunction[IRP_MJ_CREATE] = DispatchCreate;
	pDriverObj->MajorFunction[IRP_MJ_CLOSE] = DispatchCreate;
	pDriverObj->MajorFunction[IRP_MJ_DEVICE_CONTROL] = DispatchIoctl;
	pDriverObj->DriverUnload = DriverUnload;

	//取函数地址
	RtlInitUnicodeString(&routineName, L"PsGetProcessId");

	apiaddr = MmGetSystemRoutineAddress(&routineName);

	UNIQUEPROCESSID_OFFSET = *(PDWORD)(apiaddr + 3);

	//取设备名称
	RtlInitUnicodeString(&NAME, DEVICE_NAME);

	//创建通讯设备对象
	Status = IoCreateDevice(pDriverObj, 0, &NAME, FILE_DEVICE_UNKNOWN, 0, FALSE, &PDriverObject);

	if (!NT_SUCCESS(Status)) return Status;

	RtlInitUnicodeString(&SYM_NAME, DEVICE_LINK_NAME);

	//创建符号链接
	Status = IoCreateSymbolicLink(&SYM_NAME, &NAME);

	if (!NT_SUCCESS(Status))
	{
		IoDeleteDevice(PDriverObject);
		return Status;
	}

	return STATUS_SUCCESS;
}