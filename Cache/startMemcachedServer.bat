set "batPath=%cd%"
%batPath%/memcached-win64/memcached -d install
%batPath%/memcached-win64/memcached -d start
%batPath%/memcached-win64/memcached -h
pause
