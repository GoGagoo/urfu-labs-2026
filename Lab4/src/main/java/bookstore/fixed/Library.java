package bookstore.fixed;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.Nonnull;

/** Исправленный вариант Library с корректным компаратором и форматной строкой. */
public final class Library {

    private static final Comparator<Book> BOOK_COMPARATOR =
            Comparator.comparing(Book::getAuthor)
                    .thenComparing(Book::getTitle)
                    .thenComparing(Book::getSubtitle,
                            Comparator.nullsFirst(Comparator.naturalOrder()));

    private final Set<Book> books = new TreeSet<Book>(BOOK_COMPARATOR);

    public void addBook(@Nonnull Book newBook) {
        books.add(newBook);
    }

    public @Nonnull Iterable<String> describeBooksBy(@Nonnull String author) {
        List<String> result = new ArrayList<String>();
        for (Book book : books) {
            if (author.equals(book.getAuthor())) {
                result.add(String.format("%s: %s", book.getAuthor(), book.getTitle()));
            }
        }
        return result;
    }
}
