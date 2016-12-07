import json
#import MySQLdb as mysql
import sys

fname = sys.argv[1]
#dbname = sys.argv[2]
#conn = MySQLdb.connect(user = 'root', passwd= '19920930', db = '')
#cursor = conn.cursor()
#"""CREATE  TABLE {} ({} {}, {} {}) """.format()
#comment_sql = """ INSERT INTO {} VALUES ({}, {}, {}) """
out_str = ""
datas = []
with open(fname, 'r') as f:
    for line in f:
        d = json.loads(line)
        datas = []
        for x in d.values():
            if not type(x) == type(u'1') and not type(x) == type('1'):
                x = str(x)
            #x = str(x).decode('utf-8').encode('utf-8')
            datas.append(x)
        out_str += "@:".join(datas) + '\r\n'
#       out_str += "@:".join([str(x).decode('utf-8').encode('utf-8') for x in d.values()]) + '\r\n'
#    out_str = ",".join(map(str,d.keys())) + out_str
print out_str.encode('utf-8') 
#cursor.execute(comment_sql.format())
#comment = "INSERT" + "" + "" 

#cursor.commit()

