#!/bin/bash

current_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
echo $current_dir
bin_dir="$current_dir/bin"
lib_dir="$current_dir/lib"
src_dir="$current_dir/src"
temp_dir="$current_dir/src/temp"

rm -R "$bin_dir"
mkdir "$bin_dir"

if [ ! -d "$temp_dir" ]; then
  mkdir "$temp_dir"
fi

find "$src_dir" -type f -name "*.java" -exec cp -r {} "$temp_dir" \;
rm -rf "$bin_dir/*"
javac -d "$bin_dir" -cp "$lib_dir/*" "$temp_dir"/*.java
rm -R "$temp_dir"

echo "Compilation finished"