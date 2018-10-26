#!/usr/bin/env bash

# Set default value for __retries
__retries=5
__command="ls"

if [[ -z "$__command" ]]; then
	echo "ERROR: Please supply a value for '-command'"
	exit 2
fi

__retryCount=0
until $exit_code -eq 0 || [ $__retryCount -ge $__retries ]; do
	eval $__command
	exit_code=$?
   (__retryCount++)
   echo "Failed to execute command, retrying"
done

exit $exit_code