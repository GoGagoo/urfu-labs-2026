package ru.fteam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import bookstore.fixed.Book;
import bookstore.fixed.Library;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import ru.fteam.examples.buggy.NestedTernaryNullExample;
import ru.fteam.examples.buggy.PrimitiveReturnNullExample;
import ru.fteam.examples.fixed.FixedExamples;

class FixedExamplesTest {

    @Test
    void fixedTernaryPreservesSelectedWrapperType() {
        assertInstanceOf(Integer.class, FixedExamples.explicitTernary(true));
        assertInstanceOf(Double.class, FixedExamples.explicitTernary(false));
    }

    @Test
    void fixedNumericComparisonIgnoresBigDecimalScale() {
        assertTrue(FixedExamples.numericallyEqual(
                new BigDecimal("1.1"), new BigDecimal("1.10")));
    }

    @Test
    void nullableLookupUsesWrapperInsteadOfUnboxingNull() {
        assertNull(FixedExamples.getValue(100));
    }

    @Test
    void fixedLibraryHandlesNullableSubtitlesAndFormatsTitlesAsStrings() {
        Library library = new Library();
        library.addBook(new Book("Автор", "Книга", null));
        library.addBook(new Book("Автор", "Книга", "Подзаголовок"));

        List<String> descriptions = new ArrayList<String>();
        for (String description : library.describeBooksBy("Автор")) {
            descriptions.add(description);
        }

        assertEquals(2, descriptions.size());
        assertEquals("Автор: Книга", descriptions.get(0));
    }

    @Test
    void buggyNullUnboxingIsDocumentedByTests() {
        assertThrows(NullPointerException.class,
                () -> NestedTernaryNullExample.ternary(false, false));
        assertThrows(NullPointerException.class,
                () -> PrimitiveReturnNullExample.getValue(100));
    }
}
