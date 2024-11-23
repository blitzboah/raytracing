cmake_minimum_required(VERSION 3.10)
project(Raytracing)

# Specify the C++ standard
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED True)

# Add the executable
add_executable(raytracing main.cc)
