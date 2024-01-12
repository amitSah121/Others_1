file_path = "sample.txt"  # Replace with the path to your text file

while True:
    try:
        with open(file_path, 'r') as file:
            # Read the entire content of the file
            file_content = file.read()
            # Print the content to the console
            if(len(file_content) == 0): continue
            print(file_content)
            with open(file_path, 'w') as file:
                pass 
    except FileNotFoundError:
        print(f"File '{file_path}' not found.")
    except Exception as e:
        print(f"An error occurred: {str(e)}")