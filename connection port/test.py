import subprocess

#subprocess.call(['runas', '/user:Lal rishav', 'netsh wlan set hostednetwork mode=allow ssid=rishav key=password'])
#x="abcde"
#y="password"
#subprocess.call('netsh wlan set hostednetwork mode=allow ssid='+x+' key='+y,shell=True)
#subprocess.call('netsh wlan start hostednetwork ',shell=True)
results = subprocess.check_output(["netsh", "wlan", "show", "network"])
results = results.decode("ascii") # needed in python 3
results = results.replace("\r","")
ls = results.split("\n")
ls = ls[4:]
ssids = []
x = 0
while x < len(ls):
    if x % 5 == 0:
    	act = ls[x].split(":")
    	if(len(act)>1):
    		test = act[1]
    		if(len(test)>2):
    			if(test[-3:]=="@hp"):
    				print(test)
    	
        ssids.append(ls[x])
    x += 1