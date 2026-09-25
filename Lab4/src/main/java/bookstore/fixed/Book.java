package bookstore.fixed;

import java.util.Objects;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/** Исправленный Book: обязательные поля задаются в конструкторе, nullable-контракт согласован. */
public final class Book {

    private final @Nonnull String author;
    private final @Nonnull String title;
    private final @CheckForNull String subtitle;

    public Book(@Nonnull String author, @Nonnull String title, @CheckForNull String subtitle) {
        this.author = Objects.requireNonNull(author, "author");
        this.title = Objects.requireNonNull(title, "title");
        this.subtitle = subtitle;
    }

    public @Nonnull String getAuthor() {
        return author;
    }

    public @Nonnull String getTitle() {
        return title;
    }

    public @CheckForNull String getSubtitle() {
        return subtitle;
    }
}
