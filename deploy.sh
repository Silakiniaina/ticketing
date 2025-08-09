#!/bin/bash

source build.sh

assets_dir="assets"
src_dir="bin"
web_dir="web/jsp"
lib_dir="lib"
config_dir="config"

target_name="ticketing"
target_dir="/opt/tomcat/webapps/"

rm -rf "temp"
mkdir "temp"
mkdir "temp/assets"
mkdir "temp/WEB-INF"
mkdir "temp/WEB-INF/classes"
mkdir "temp/WEB-INF/lib"
mkdir "temp/WEB-INF/views"

cp -r "$assets_dir"/* "temp/assets"
cp -r "$lib_dir"/* "temp/WEB-INF/lib"
cp -r "$src_dir"/* "temp/WEB-INF/classes"
cp -r "$web_dir"/index.jsp "temp/"
cp -r "$web_dir"/* "temp/WEB-INF/views"
cp -r "$config_dir"/* "temp/WEB-INF"

jar -cf "$target_name.war" -C temp/ .

cp "$target_name.war" "$target_dir"
rm "$target_name.war"
rm -rf "temp"