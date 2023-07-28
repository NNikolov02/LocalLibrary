# My Spring Project
  # Local Library

A local library wants to organize its items in an electronic information system, which will keep track of all its paper books and e-books. 
Features the product will need to support
There are two types of books in the library – paper books and ebooks.
For every book you must store information for the book title, the authors of the book, genre, a short summary of its content, ISBN and one or more tags related to the content of the book which are necessary for sorting the books in categories. 
If a book is an electronic book it must have a link where you can read it online. However, some of the books may as well be downloaded for free.
So, such books must also provide a download link. 
If it is a paper book you must keep the number of copies available at the moment, as well as the total number (including the borrowed ones).
Regarding the authors of the books, the information for them must include their name, country, date of birth and date of death (if not alive).
For a person to get access to the books’ information, they must have a profile in the system. 
Besides the users’ credentials you must keep track of the users’ names, age, gender, address, city, country and email. 
And don’t forget about the EU GDPR, you must make sure that all users give their consent to manage their data before persisting any part of it.
Each user should be able to borrow a paper book, or access the online e-books for reading and/or downloading.
The system must keep a history of all the books that they have borrowed from the library, read online or downloaded. 
If a user has borrowed a book, then they must be able to see when the final return date is, as well as to be able to request a postponement of the return date. 
A user may ask many times for a postponement, but the total amount of delay days must not exceed 14 days. 
After these 14 days the user must be provided with an appropriate message when attempting to borrow a different book, and should be denied that request.
Finally, we need to support search functionality to ease finding and borrowing books. 
You must provide the users with the ability to search for books by their name, genre, tags, as well as by authors’ first and/or last names.