from lxml import etree
import gzip


# Assuming you've downloaded the DTD and saved it as 'pubmed_240101.dtd' in the current directory
dtd_file = './input/pubmed_240101.dtd'

# Load the DTD from the local file
with open(dtd_file, 'r') as dtd_content:
    dtd = etree.DTD(dtd_content)

# Step 2: Load your compressed XML file
# Replace 'your_compressed_xml_file.xml.gz' with the path to your actual compressed XML file
compressed_xml_file = './input/pubmed24n0001.xml.gz'

# Decompress and parse the XML document
with gzip.open(compressed_xml_file, 'rb') as xml_content:
    xml_document = etree.parse(xml_content)

# Step 3: Validate the XML document against the DTD
is_valid = dtd.validate(xml_document)

print(f"Document is {'valid' if is_valid else 'invalid'} according to the DTD.")

# If the document is invalid, print the reason
if not is_valid:
    print(dtd.error_log.filter_from_errors()[0])
