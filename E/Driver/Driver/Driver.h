#pragma once
#include <ntddk.h>
#include <windef.h>
#define DEVICE_NAME				L"\\Device\\Feng2022"
#define DEVICE_LINK_NAME	L"\\DosDevices\\Feng2022"

#define IOCTL_IO_SetPROTECT		 CTL_CODE(FILE_DEVICE_UNKNOWN, 0x2021, METHOD_BUFFERED, FILE_ANY_ACCESS)	
#define IOCTL_IO_ReSetPROTECT    CTL_CODE(FILE_DEVICE_UNKNOWN, 0x2022, METHOD_BUFFERED, FILE_ANY_ACCESS)	


//定义结构
typedef struct _DataStruct {
	HANDLE	ProcessPid;
	HANDLE	SetLongPid;
} DataStruct, *PDataStruct;

NTKERNELAPI	NTSTATUS	PsLookupProcessByProcessId(_In_ HANDLE ProcessId, _Outptr_ PEPROCESS* Process);