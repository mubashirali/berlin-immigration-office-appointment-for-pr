#!/bin/bash
	ps aux | grep '[r]un.sh' | awk '{print $2}' | xargs kill -9
	ps aux | grep '[r]un.sh' | awk '{print $2}' | xargs kill -9
