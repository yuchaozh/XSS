import csv
import urllib2
import urllib
import httplib
from bs4 import BeautifulSoup
from socket import error as SocketError


csv_file = file('sub_links.txt', 'rb')
reader = csv.reader(csv_file)
url_array = []
count = 0
f = open('result.txt','aw')
ef = open('result_error.txt', 'aw')
for x in reader:
	url_array.append(x[0])
for i in range (0, 706):
	count = count + 1
	#user_agent = 'Mozilla/4.0 (compatible; MSIE 5.5; Windows NT)'
	user_agent = 'Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.102 Safari/537.36'
	headers = { 'User-Agent' : user_agent }
	values = {'name' : 'Yuchao Zhou',
	          'location' : 'Evanston',
	          'language' : 'Python' }
	data = urllib.urlencode(values)
	req = urllib2.Request(url_array[i], data, headers)
	try:
		response = urllib2.urlopen(req)
		print str(count) + ": " + url_array[i] +'\n'
		f.write(str(count) + ": " + url_array[i] +'\n')
		f.write(str(response.info()) + '\n')
	except urllib2.HTTPError:
		try:
			# GET request
			response = urllib2.urlopen(url_array[i])
			#print str(response.info())

			f.write(str(count) + ": " + url_array[i] +'\n')
			f.write(str(response.info()) + '\n')
		except urllib2.HTTPError:
			#error_count = error_count + 1
			ef.write(str(count) + ": " + url_array[i] +'\n')
			print "HTTPError.....pass......."
	except urllib2.HTTPError:
		ef.write(str(count) + ": " + url_array[i] +'\n')
		print "HTTP error.....pass......."
	except urllib2.URLError:
		ef.write(str(count) + ": " + url_array[i] +'\n')
		print "URL error.....pass......."
	except SocketError:
		ef.write(str(count) + ": " + url_array[i] +'\n')
		print "socket error....pass......"
	except Exception, e:
		ef.write(str(count) + ": " + url_array[i] +'\n')
		print "Exception error......"

