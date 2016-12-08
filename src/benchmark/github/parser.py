#!/usr/bin/env python

import json, csv, time, os, itertools
from itertools import islice, chain

ts = '2016-11'
try:
    os.remove('tmp/' + ts + '.tmp.json')
    os.remove('csv/' + ts + '.csv')
except Exception as e:
    print e

tmp = open('tmp/' + ts + '.tmp.json', 'wb+')
tmp.write('[')

dummy = 0
counter = 0
max_len = 0
with open('json/' + ts + '.json') as f:
    for line in f:
        obj = json.loads(line)
        # print obj['created_at'], obj['id'], obj['repo'], obj['type'], obj['public']

        new_obj = {}
        new_obj['created_at'] = obj['created_at']
        new_obj['id'] = obj['id']
        new_obj['type']  = obj['type']
        new_obj['repo']  = obj['repo']['name']
        new_obj['user'] = obj['actor']['login']

        payload = obj['payload']
        if 'commits' in obj['payload'] and payload['size'] > 0:
            email = payload['commits'][0]['author']['email']
            new_obj['user_email'] = email
        else:
            new_obj['user_email'] = ""
        

        if 'org' in obj:
            new_obj['org'] = obj['org']['login']
        else:
            new_obj['org'] = ""

        # print new_obj
        line_tmp = json.dumps(new_obj)
        if counter > 0:
            line_tmp = ', ' + line_tmp 
        tmp.write(line_tmp)
        counter += 1

        if counter % 100 == 0:
            print counter

print 'total num obj: ', counter
tmp.write(']')
f.close()
tmp.close()

tmp = open('tmp/' + ts + '.tmp.json', 'r')
csv_file = open('csv/' + ts + '.csv', 'w')
objs_parsed = json.loads(tmp.read())
writer = csv.writer(csv_file)
count = 0
for data in objs_parsed:
    if count == 0:
        header = data.keys()
        writer.writerow(header)

    vl = []
    for v in data.values():
        v_tmp = v
        if isinstance(v, str):
            v_tmp = unicode(v, 'utf-8')
        v_tmp2 = v_tmp.encode('utf-8')
        vl.append(v_tmp2)

    writer.writerow(vl)
    count += 1
print "Total values: ", count

tmp.close()
csv_file.close()
