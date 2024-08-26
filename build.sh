#
#docker  build --no-cache \
#--progress=plain --network=host \
#-t m2:1 -f Dockerfile . \
#&& docker run --cpus=0.1 --rm --network host m2:1 \
#&& exit
