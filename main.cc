#include "color.h"
#include "vec3.h"
#include "ray.h"

#include <iostream>

color ray_color(const ray& r){
  vec3 unit_direction = unit_vector(r.direction());
  auto a = 0.5*(unit_direction.y() + 1.0);
  return(1.0-a)*color(1.0,1.0,1.0) +a*color(0.5, 0.7, 1.0);
}

int main(){
  //image 
  

  auto aspect_ratio = 16.0/9.0;
  int image_width = 400;
  
  //calulate image height, and ensure it's >=1

  int image_height = int(image_width / aspect_ratio);
  image_height = (image_height < 1) ? 1 : image_height;

  //camera

  auto focal_length = 1.0;
  auto viewport_height = 2.0;
  auto viewport_width = viewport_height * (double(image_width)/image_height);
  auto camera_center = point3(0,0,0);

  //calculate the vectors across the horiz and down the vertical viewport edges

  auto viewport_u = vec3(viewport_width,0,0);
  auto viewport_v = vec3(0,-viewport_height,0);

  //calulate the horiz and vertical delta vecs from pixel 2 pixel
  auto pixel_delta_u = viewport_u / image_width;
  auto pixel_delta_v = viewport_v / image_height;

  //calulate location of upp left pixel
  auto viewport_upper_left = camera_center 
                              - (vec3 (0,0,focal_length) - viewport_u/2 - viewport_v/2);
  auto pixel00_loc = viewport_upper_left + 0.5 * (pixel_delta_u + pixel_delta_v);

  //render
  std::cout << "P3\n" << image_width << ' ' << image_height << "\n255\n";
  for(int j=0; j<image_height; j++){
    std::clog << "\rscanlines remaining: " << (image_height - j) << ' ' << std::flush;
    for(int i=0; i<image_width; i++){
      auto pixel_center = pixel00_loc + (i * pixel_delta_u) * (j * pixel_delta_v);
      auto ray_dir = pixel_center - camera_center;
      ray r(camera_center, ray_dir);

      color pixel_color = ray_color(r);
      write_color(std::cout, pixel_color);
    }
  }

  std::clog << "\rdone.\n";
}
