package test;

import static org.junit.Assert.assertEquals;

import java.time.format.DateTimeParseException;

import org.junit.Test;

import models.Post;

/**
 * A test class for the Post class to ensure its methods and
 * constructors are working correctly.
 */
public class PostTest {

	/**
	 * Test if the constructor of the Post class initializes the fields correctly.
	 */
	@Test
	public void testConstructor() {
		System.out.println("Running PostTest testConstructor...");
		Post post = new Post("Content", "Author", 10, 5, "1/01/2021 10:00", 1);

		// Verify all initialized values are as expected
		assertEquals("Content", post.getContent());
		assertEquals("Author", post.getAuthor());
		assertEquals(10, post.getLikes());
		assertEquals(5, post.getShares());
	}

	/**
	 * Test if the toString method of the Post class produces the correct format.
	 */
	@Test
	public void testToString() {
		System.out.println("Running testToString...");
		Post post = new Post(1, "Content", "Author", 10, 5, "1/01/2021 10:00", 1);
		assertEquals("1 | Content | Author | 10 | 5 | 1/01/2021 10:00", post.toString());
	}

	/**
	 * Test to ensure that the Post constructor throws an exception when given an
	 * invalid date format.
	 */
	@Test(expected = DateTimeParseException.class)
	public void testInvalidDateFormat() {
		System.out.println("Running testInvalidDateFormat...");
		// Attempt to create a Post with an invalid date format
		new Post(1, "Content", "Author", 10, 5, "invalid-date", 1);
	}

	/**
	 * Test the boundary values for likes and shares.
	 */
	@Test
	public void testLikesSharesBoundary() {
		System.out.println("Running testLikesSharesBoundary...");
		// Create a post with maximum and minimum integer values for likes and shares
		Post post = new Post(1, "Content", "Author", Integer.MAX_VALUE, Integer.MIN_VALUE, "1/01/2021 10:00", 1);
		assertEquals(Integer.MAX_VALUE, post.getLikes());
		assertEquals(Integer.MIN_VALUE, post.getShares());
	}
}
