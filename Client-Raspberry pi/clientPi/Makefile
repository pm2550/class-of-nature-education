# 编译器
CXX = g++

# 编译选项
CXXFLAGS = -Wall

# 输出的可执行文件名
OUTPUT = my_program

# 源文件
SOURCES = 源.cpp hello.cpp request.cpp pipeline_push.cpp
# 对应的目标文件 (object files)
OBJECTS = $(SOURCES:.cpp=.o)

# 默认目标
all: $(OUTPUT)

# 如何从源文件创建目标文件
.cpp.o:
	$(CXX) $(CXXFLAGS) -c $< -o $@

# 如何从目标文件创建可执行文件
$(OUTPUT): $(OBJECTS)
	$(CXX) $(OBJECTS) -o $(OUTPUT) -I/usr/include/gstreamer-1.0 -I/usr/include/arm-linux-gnueabihf -I/usr/include/glib-2.0 -I/usr/lib/arm-linux-gnueabihf/glib-2.0/include -lgstreamer-1.0 -lgobject-2.0 -lglib-2.0 -I/usr/include/gstreamer-1.0 -I/usr/include/glib-2.0 -I/lib/arm-linux-gnueabihf/glib-2.0/include -I/home/pm/gst-rtsp-server -lgstrtspserver-1.0 -lpthread

# 清除构建文件
clean:
	rm -f $(OBJECTS) $(OUTPUT)
