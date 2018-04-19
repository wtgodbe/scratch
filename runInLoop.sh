#!/usr/bin/env bash

count=0

run_test_in_loop()
{
	while :
	do
		echo "In loop $count"
		echo "$1/dotnet xunit.console.netcore.exe $2 -method $3"
		$1/dotnet xunit.console.netcore.exe $2 -method $3
		if [[ $? != 0 ]]; then exit; fi
		((count++))
	done
}

for i in {1..100}
do
	run_test_in_loop $1 $2 $3 &
done
wait

exit 0