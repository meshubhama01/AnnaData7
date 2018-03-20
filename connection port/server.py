import socket
import threading
import os
import pickle
from PIL import ImageGrab

def download(name,socket):
	fileName = socket.recv(1024)
	print("recieved")
	if os.path.isfile(fileName):
		#socket.send("EXISTS " + str(os.path.getsize(fileName)))
		#print(os.path.getsize(fileName))
		#response = socket.recv(1024)
		#if response[:2]== 'OK':
		print(fileName)
		print("file exists")
		socket.send("File Exists")
		socket.send(str(os.path.getsize(fileName)))
		with open(fileName,'rb') as f:
			bytes = f.read(1024)
			socket.send(bytes)
			while bytes!="":
				bytes = f.read(1024)
				socket.send(bytes)
				print("sending...")
	else:
		print("not exists")
		socket.send("File don't exists")

	socket.close()

def Main():
	hostname = socket.gethostname()

	host =  socket.gethostbyname(hostname)
	#host = '192.168.0.102'
	port = 3500
	s = socket.socket()
	s.bind((host,port))
	s.listen(5)

	print("server started on ip " + host)

	while True:
		c, addr = s.accept()
		print(addr)
		print("client connected")
		c.send(hostname)
		videos = []
		images = []
		docs = []
		others = []
		installer = []
		

		for path, subdirs, files in os.walk('./www'):
			for name in files:
				loc = os.path.join(path, name)
				print(loc)
				getFileName = loc.split("\\")
				getFileName = getFileName[len(getFileName)-1]
				ext = (getFileName.split('.'))[1]
				if ext == "mp4" or ext =="MP4" or ext =="mkv" or ext == "flv" or ext== "3gp":
					videos.append(getFileName)
				elif ext == "txt" or ext == "docx" or ext == "ppt":
					docs.append(getFileName)
				elif ext == "mp3" or ext == "MP3":
					songs.append(getFilename)
				elif ext=="exe":
					installer.append(getFileName)
				elif ext == "jpeg":
					image = ImageGrab.grab()
					image.save(r'C:\Users\shubham\Desktop\Python\ScreenShots\screen.jpg')        #when image is at local disk
					file = open('C:\Users\shubham\Desktop\Python\ScreenShots\screen.jpg', 'rb')
                                        content = file.read()
					images.append(content)
					
				else:
					others.append(others)

		files = [videos,docs]
		c.send(pickle.dumps(files))
		t = threading.Thread(target=download,args=("download",c))
		t.start()
	s.close()

if __name__ == '__main__':
	Main()