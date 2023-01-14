java -jar JavaDllPacker.jar UZI.jar classes.h
g++ -Wall -DBUILD_DLL -O2 -std=c++11 -m64 -c main.cpp -o main.o
g++ -shared -Wl,--dll main.o -o UZI.dll -s -m64 -static -static-libgcc -static-libstdc++ -luser32