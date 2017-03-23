from PIL import ImageFont
from PIL import Image
from PIL import ImageDraw

def text2png(text, fullpath, color = "#000", bgcolor = "#FFF", fontfullpath = None, heading =None, fontsize = 13, headingfontsize=30, headingpaddingleft=30, leftpadding = 3, toppadding = 3, rightpadding = 3, width = 200, height=600):
	REPLACEMENT_CHARACTER = u'\uFFFD'
	NEWLINE_REPLACEMENT_STRING = ' ' + REPLACEMENT_CHARACTER + ' '

	headingy = 0
	font = ImageFont.load_default() if fontfullpath == None else ImageFont.truetype(fontfullpath, fontsize)
	text = text.replace('\n', NEWLINE_REPLACEMENT_STRING)

	lines = []
	line = u""	

	for word in text.split():
		if word == REPLACEMENT_CHARACTER: #give a blank line
			lines.append( line[1:] ) #slice the white space in the begining of the line
			line = u""
			#lines.append( u"" ) #the blank line
		elif font.getsize( line + ' ' + word )[0] <= (width - rightpadding - leftpadding):
			line += ' ' + word
		else: #start a new line
			lines.append( line[1:] ) #slice the white space in the begining of the line
			line = u""

			#TODO: handle too long words at this point
			line += ' ' + word #for now, assume no word alone can exceed the line width

	if len(line) != 0:
		lines.append( line[1:] ) #add the last line

	line_height = font.getsize(text)[1]

	if heading !=None:
		fontheading = ImageFont.truetype(fontfullpath, headingfontsize)
		headingy = fontheading.getsize(heading)[1]

	img = Image.new("RGBA", (width, height), bgcolor)
	draw = ImageDraw.Draw(img)
	
	y = toppadding
	draw.text((headingpaddingleft,y),heading,color,font=fontheading)
	y+=headingy+10
	
	for line in lines:
		draw.text( (leftpadding, y), line, color, font=font)
		
		y += line_height

	
	img.save(fullpath)

def merge_images(file1, file2,path):
	"""Merge two images into one, displayed side by side
	:param file1: path to first image file
	:param file2: path to second image file
	:return: the merged Image object
	"""
	image1 = Image.open(file1)
	image2 = Image.open(file2)

	(width1, height1) = image1.size
	(width2, height2) = image2.size

	result_width = width1 + width2
	result_height = max(height1, height2)

	result = Image.new('RGB', (result_width, result_height))
	result.paste(im=image1, box=(0, 0))
	result.paste(im=image2, box=(width1, 0))
	result.save(path,"png")
	