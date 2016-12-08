import pandas as pd
import sys
import json
fname = sys.argv[1]
outfname = sys.argv[2]
num = 100
outf = open(outfname, 'a')
with open(fname, 'r') as fid:
    count1 = 1
    count = 0 
    instr = "["
    for line in fid:

#        jsond = json.loads(line)
#        d = pd.DataFrame(line)
        instr += line + ','
        if count1 % 100000 == 0:
            instr = instr[:-1]
            instr += "]"
            jsond = json.loads(instr)
            d = pd.DataFrame(jsond)

            #d = pd.read_json(line)
            if count == 0:
                header = True
            else :
                header = False
            d.to_csv(outf, sep = "<", header = header,  encoding= 'utf-8')     
            if count1 % 100000 == 0:
                print """process{}""".format(count1)
            instr = "["
            count += 1
        count1 += 1
outf.close()

#    print "convert json file"  
#    out_str = '[' + ",".join(datas) + ']'
#    print "load json"
#    json.loads(out_str)
#    print "load success"
#    d = pd.read_json(out_str)
#    print "convert csv"
#    (nrows, ncols) = d.shape
#    outf = open(outfname, 'a')
#    if nrows > 10e6:
#        num = 50
#        for i in range(num):
#            print "process", i,"/", num
#            start = nrows / num * i 
#            end = nrows / num * (i + 1)
#            if i == num - 1:
#               d[start:].to_csv(outf, sep = '<', encoding = 'urf-8')
#               continue
#            d[start:end].to_csv(outf, sep = '<', encoding = 'utf-8')
#    d.to_csv(outfname, sep = '<', encoding = 'utf-8')
#    size = len(datas)
    
#fname = sys.argv[2]
#for i in range(num):
#    print "process", i
#    start = int(size / num * i )
#    end = int(size / num * (i+1))
#    json_str = '[' + ",".join(datas[start:end]) + ']'
#    pd.read_json(json_str).to_csv(fname + str(i), sep = '<' , encoding = 'utf-8')
#fname = sys.argv[2]
#with open(fname, 'w') as fid:
#    fid.write(out_str)
    
