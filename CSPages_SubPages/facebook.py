import csv
import urllib2
import urllib
import re
import httplib
from bs4 import BeautifulSoup
from socket import error as SocketError


def parse(current_url):
	global count
	global error_count
	count = count + 1
	# response_3.txt
	user_agent = 'Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.102 Safari/537.36'
	# response_4.txt 
	#user_agent = 'Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Firefox/24.0'
	headers = { 'User-Agent' : user_agent }
	values = {'name' : 'Yuchao Zhou',
	          'location' : 'Evanston',
	          'language' : 'Python' }
	data = urllib.urlencode(values)
	# POST request
	req = urllib2.Request(current_url, data, headers)
	try:
		response = urllib2.urlopen(req)
		print current_url
		f.write(str(count) + ": " + current_url +'\n')
		f.write(str(response.info()) + '\n')

	except urllib2.HTTPError:
		try:
			# GET request
			response = urllib2.urlopen(current_url)
			#print str(response.info())

			f.write(str(count) + ": " + current_url +'\n')
			f.write(str(response.info()) + '\n')
		except urllib2.HTTPError:
			error_count = error_count + 1
			ef.write(str(count) + ": " + current_url +'\n')
			ef.write(str(error_count) + ": " + "HTTPError")
			print "HTTPError.....pass......."
	except urllib2.URLError:
		error_count = error_count + 1
		ef.write(str(count) + ": " + current_url +'\n')
		ef.write(str(error_count) + ": " + "URLError")
		print "URLError.....pass......."
	except SocketError:
		error_count = error_count + 1
		ef.write(str(count) + ": " + current_url +'\n')
		ef.write(str(error_count) + ": " + "SocketError")
		print "SocketError....pass......"
	except Exception, e:
		error_count = error_count + 1
		ef.write(str(count) + ": " + current_url +'\n')
		ef.write(str(error_count) + ": " + "Exception")
		print "Exception error......"



if __name__ == "__main__":
	#csv_file = file('top-1m.csv', 'rb')
	csv_file = file('CSP_home.txt', 'rb')
	reader = csv.reader(csv_file)
	sub_url = []
	url_array = []
	global error_count
	error_count = 0
	global count
	count = 0
	count_dict = 0
	f = open('sub_links2.txt','aw')
	ef = open('links_error2.txt', 'aw')
	#f = open('response_sub.txt','aw')
	#ef = open('error_sub.txt', 'aw')
	for x in reader:
		url_array.append(x[0])
	#print url_array
	for i in range (20, 23):
		print str(count_dict) + " : " + url_array[i]
		#parse(url_array[i])
		f.write(url_array[i] + "\r\n")
		try:
			response = urllib2.urlopen(url_array[i])
			html = response.read()
			soup = BeautifulSoup(html)
			#print(soup.prettify())
			link_array = soup.find_all(href=re.compile("http"))
			for x in link_array:
				try:
					f.write(x.get('href') + "\r\n")
				except UnicodeEncodeError:
					ef.write(str(x) + "\r\n")
			count_dict = count_dict + 1
			f.write("\r\n")
		except urllib2.HTTPError:
			error_count = error_count + 1
			ef.write(url_array[i] +'\r\n')
			print "HTTPError.....pass......."

