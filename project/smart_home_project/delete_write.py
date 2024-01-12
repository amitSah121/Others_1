# Specify the file path where you want to write the content
file_path = "sample.txt"  # Replace with the path to your text file

# Content you want to write to the file
while True:
    content_to_write = input("Enter sth: ")

    try:
        
        with open(file_path, 'w+') as file:
            # Write the content to the file
            file_content = file.read()
            if(len(file_content) == 0 ):
                file.write(content_to_write)
        # print(f"Content has been successfully written to '{file_path}'.")
    except Exception as e:
        print(f"An error occurred: {str(e)}")
