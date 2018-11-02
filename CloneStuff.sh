#!/usr/bin/env bash

# Set default value for __retries
__retries=5
__command=""

while :
do
    if [ $# -le 0 ]; then
        break
    fi

    lowerI="$(echo $1 | awk '{print tolower($0)}')"
    case $lowerI in
        command|-command)
            if [ -n "$2" ]; then
              __command="$2"
              shift
            else
              echo "ERROR: 'command' requires a non-empty option argument"
              exit 1
            fi
            ;;
        retries|-retries)
            if [ -n "$2" ]; then
              __retries="$2"
              shift
            else
              echo "ERROR: 'retries' requires a non-empty option argument"
              exit 1
            fi
            ;;
        *)
    esac

    shift
done

if [[ -z "$__command" ]]; then
	echo "ERROR: Please supply a value for '-command'"
	exit 2
fi

exit_code=1
__retryCount=0
until [ $exit_code -eq 0 ] || [ $__retryCount -ge $__retries ]; do
	eval $__command
	exit_code=$?
   __retryCount=$((__retryCount+1))
   if [ $exit_code -ne 0 ]; then
    echo "$__command exited with $exit_code, retrying"
  fi
done

echo "$__command exited with $exit_code"
exit $exit_code