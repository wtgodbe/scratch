#!/usr/bin/env bash

count=0

while :
do
	echo "In loop $count"
	$1/dotnet xunit.console.netcore.exe $2 -method $3
	if [[$? != 0]]; then exit; fi
	((count++))
done

exit 0