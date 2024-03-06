while True:
    # message = input("Test 1: ")
    try:
        message = input("Chat 1: ")
        with open('file_1.txt', 'w') as file:
            file.write(message)
    except FileNotFoundError:
        print("File not found.")
    except Exception as e:
        print("An error occurred:", str(e))
