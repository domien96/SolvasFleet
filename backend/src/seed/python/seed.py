import psycopg2
#De bedoeling van dit python script is om de postgres database te vullen, zodat we eenvoudig de frontend kunnen testen

#We gaan ervan uit dat de databank leeg is (start bvb eerst de backend met profile clean). Reden=truncate zorgt voor een enorme overhead


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

try:
    conn = psycopg2.connect(dbname="vop", user="vop", password="vop")
    cursor = conn.cursor()
    insertEntities(cursor,["users","companies","vehicle_types","vehicles"])
    conn.commit()
    conn.close()


except psycopg2.Error as e:
    print (e.pgerror)
