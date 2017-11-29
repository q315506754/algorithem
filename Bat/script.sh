# strict mode
#set -u
#还有另一种写法-o nounset，两者是等价的。
#set -o nounset

#用来在运行结果之前，先输出执行的那一行命令。
set -x
#set -o xtrace

#它使得脚本只要发生错误，就终止执行。
#但是管道中只要最后一条命令成功（返回0），后面继续执行
#set -e
#set -o errexit

#只要一个子命令失败，整个管道命令就失败，脚本就会终止执行。
#set -eo pipefail

echo $a
echo bar

foo
#command1 || exit 1

# 写法一
#command1 || { echo "command1 w1 failed"; exit 1; }

# 写法二
#if ! command1; then echo "command1 w2 failed"; exit 1; fi

# 写法三
command1
if [ "$?" -ne 0 ]; then echo "command1 w3 failed"; exit 1; fi

echo exit

