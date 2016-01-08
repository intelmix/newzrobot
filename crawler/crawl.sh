echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" >> /tmp/crawl.log
cd /srv/newzrobot/crawler
/usr/bin/java -jar target/newzrobot-crawler-0.1.0.jar >> /tmp/crawl.log
