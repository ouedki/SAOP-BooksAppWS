package com.BooksWebService.soap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.BooksWebService.books.BookDetails;
import com.BooksWebService.books.DeleteBookDetailRequest;
import com.BooksWebService.books.DeleteBookDetailResponse;
import com.BooksWebService.books.GetAllBookDetailRequest;
import com.BooksWebService.books.GetAllBookDetailResponse;
import com.BooksWebService.books.GetBookDetailRequest;
import com.BooksWebService.books.GetBookDetailResponse;
import com.BooksWebService.soap.beans.Book;
import com.BooksWebService.soap.exceptions.BookNotFoundException;
import com.BooksWebService.soap.services.BookDetailsService;
import com.BooksWebService.soap.services.BookDetailsService.Status;

@Endpoint
public class BooksDetailsEndpoint {
	
	@Autowired
	BookDetailsService service;
	
	@PayloadRoot(namespace="http://BooksWebService.com/books", localPart="getBookDetailRequest")
	@ResponsePayload
	public GetBookDetailResponse processBookDetailsRequest (@RequestPayload GetBookDetailRequest request){
		Book book = service.findById(request.getId());
		if (book==null) {
			throw new BookNotFoundException("Book Not Found ID:" + request.getId());
		}
		return mapBookDetail(book);
	}

	private GetBookDetailResponse mapBookDetail(Book book) {
		GetBookDetailResponse response = new GetBookDetailResponse();
		response.setBookDetails(mapBook(book));
		return response;
	}

	private BookDetails mapBook(Book book) {
		BookDetails bookDetails = new BookDetails();
		
		bookDetails.setId(book.getId());
		bookDetails.setName(book.getName());
		bookDetails.setAuthor(book.getAuthor());
		return bookDetails;
	}

	private GetAllBookDetailResponse mapAllBookDetail(List<Book> books) {
		GetAllBookDetailResponse response = new GetAllBookDetailResponse();
		for(Book book : books) {
			BookDetails mapBook = mapBook(book);
			response.getBookDetails().add(mapBook);
		}
		return response;
	}

	@PayloadRoot(namespace="http://BooksWebService.com/books", localPart="getAllBookDetailRequest")
	@ResponsePayload
	public GetAllBookDetailResponse processAllBookDetailsRequest (@RequestPayload GetAllBookDetailRequest request){
		List<Book> books = service.findAll();
		return mapAllBookDetail(books);
		
	}
	
	@PayloadRoot(namespace="http://BooksWebService.com/books", localPart="deleteBookDetailRequest")
	@ResponsePayload
	public DeleteBookDetailResponse processDeleteBookDetailsRequest (@RequestPayload DeleteBookDetailRequest request){
		Status status = service.deleteById(request.getId());
		DeleteBookDetailResponse response = new DeleteBookDetailResponse();
		response.setStatus(mapStatus(status));;
		return response;
	}

	private com.BooksWebService.books.Status mapStatus(Status status) {
		if (status==Status.SUCCESS) {
			return com.BooksWebService.books.Status.SUCCESS;
		}
		return com.BooksWebService.books.Status.FAILURE;
	}


}
