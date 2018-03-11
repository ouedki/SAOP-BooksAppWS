package com.BooksWebService.soap.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.BooksWebService.soap.beans.Book;

@Component
public class BookDetailsService {
	
	public enum Status{
		SUCCESS,FAILURE;
	}
	
	private static List<Book> books = new ArrayList<>();
		static {
			Book book1 = new Book(1, "book1", "author1");
			books.add(book1);
			Book book2 = new Book(2, "book2", "author2");
			books.add(book2);
			Book book3 = new Book(3, "book3", "author3");
			books.add(book3);
			Book book4 = new Book(4, "book4", "author4");
			books.add(book4);
		}
		
		public Book findById(int id) {
			for (Book book : books) {
				if (book.getId() == id)
					return book;
			}
			return null;
		}

		// courses
		public List<Book> findAll() {
			return books;
		}

		public Status deleteById(int id) {
			Iterator<Book> iterator = books.iterator();
			while (iterator.hasNext()) {
				Book course = iterator.next();
				if (course.getId() == id) {
					iterator.remove();
					return Status.SUCCESS;
				}
			}
			return Status.FAILURE;
		}
}
