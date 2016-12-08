
```bash
mkdir github json tmp csv
cd github
wget http://data.githubarchive.org/2016-11-{01..30}-{0..23}.json.gz # it will take a while
gunzip *.gz
mv *.json json/
cd json
cat *.json >> 2016-11 # don't name it sth.json!!!
mv 2016-11 2016-11.json
cd ../
vim parser.py # find ts and change it to 2016-11
python parser.py
mv csv/2016-11.csv output.csv
csvsql --dialect mysql --snifflimit 65534 output.csv > tabledef.sql
cat tabledef.sql # copy and paste this output to mysql console
mysqlimport --ignore-lines=1 --fields-terminated-by=, --local -u user -p github output.csv 
# password is password
```

