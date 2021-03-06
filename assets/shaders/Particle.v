#version 460

layout(location=0) in vec3 pos;
layout(location=1) in vec2 uv;
layout(location=2) in vec4 color;
layout(location=3) in float tex;

out VO{ vec2 uv; vec4 color; float tex; } vertout;

uniform mat4 u_proj;
uniform mat4 u_world;

void main(void){
    gl_Position=u_proj*u_world*vec4(pos,1.0);

    vertout.uv=uv;
    vertout.color=color;
    vertout.tex=tex;
}