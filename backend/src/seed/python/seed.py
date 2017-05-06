import psycopg2
#De bedoeling van dit python script is om de postgres database te vullen, zodat we eenvoudig de frontend kunnen testen

def getEntities(filename):
    file=open(filename,"r")
    rows=[]
    for line in file:
        rows.append(line)
    file.close()
    return rows


def insertEntities(cursor,tablenames):
    for table in tablenames:
        rows=getEntities(table+".csv")
        columns=rows[0]
        for entity in rows[1:]:
            sql="INSERT INTO "+table+" ("+columns+") VALUES("+entity+");"
            cursor.execute(sql)

def clear(cursor,tablenames):
    for table in tablenames:
        cursor.execute("DELETE FROM "+table)



def resetSeq(cursor,tablename,idname):
    cursor.execute("SELECT setval('"+tablename+"_"+idname+"_seq', (SELECT MAX("+idname+") FROM "+tablename+"));")

try:
    conn = psycopg2.connect(dbname="vop", user="vop", password="vop")
    cursor = conn.cursor()
    tables=["users","companies","vehicles","fleets","fleet_subscriptions","contracts","functions"]
    clear(cursor,reversed(tables))
    insertEntities(cursor,tables)
    resetSeq(cursor,"users","user_id")
    resetSeq(cursor,"companies","company_id")
    resetSeq(cursor,"vehicles","vehicle_id")
    resetSeq(cursor,"fleets","fleet_id")
    resetSeq(cursor,"functions","function_id")

    resetSeq(cursor,"fleet_subscriptions","fleet_subscription_id")
    resetSeq(cursor,"contracts","contract_id")
    conn.commit()
    conn.close()

except psycopg2.Error as e:
    print (e.pgerror)
