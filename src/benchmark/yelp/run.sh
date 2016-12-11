user=root
passwd=password
db=583proj
echo "convert data to csv"
#python parse.py yelp_academic_dataset_business.json business.csv
#python parse.py yelp_academic_dataset_checkin.json checkin.csv
#python parse.py yelp_academic_dataset_tip.json tip.csv
#python parse.py yelp_academic_dataset_user.json user.csv
#python parse.py yelp_academic_dataset_review.json reviews.csv
echo "create table"
echo "use $db;" > tb_create.sql
csvsql -d \< --dialect mysql --snifflimit 65534 business.csv >> tb_create.sql
csvsql -d \< --dialect mysql --snifflimit 65534 checkin.csv >> tb_create.sql
csvsql -d \< --dialect mysql --snifflimit 65534 tip.csv >> tb_create.sql
csvsql -d \< --dialect mysql --snifflimit 65534 user.csv >> tb_create.sql
csvsql -d \< --dialect mysql --snifflimit 65534 review.csv >> tb_create.sql
echo "load data"
mysqlimport --ignore-lines=1 --fields-terminated-by=\< --local -u $user -p$passwd $db business.csv
echo "load bussiness"
mysqlimport --ignore-lines=1 --fields-terminated-by=\< --local -u $user -p$passwd $db checkin.csv
echo "load checkin"
mysqlimport --ignore-lines=1 --fields-terminated-by=\< --local -u $user -p$passwd $db tip.csv
echo "load tip"
mysqlimport --ignore-lines=1 --fields-terminated-by=\< --local -u $user -p$passwd $db user.csv
echo "load user"
mysqlimport --ignore-lines=1 --fields-terminated-by=\< --local -u $user -p$passwd $db review.csv
echo "load review"
echo "run queries"
javac -classpath mysql-connector.jar main.java
java main
