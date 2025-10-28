package com.example.baitap1.viewmodel


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.baitap1.model.Book
import com.example.baitap1.model.Student

class LibraryViewModel : ViewModel() {
    // Danh sách sách
    private val _books = mutableStateListOf<Book>(
        Book("1", "Sách 01"),
        Book("2", "Sách 02")
    )
    val books: List<Book> = _books

    // Danh sách sinh viên
    private val _students = mutableStateListOf<Student>()
    val students: List<Student> = _students

    // Trigger để force recomposition
    private val _updateTrigger = mutableStateOf(0)
    val updateTrigger = _updateTrigger

    init {
        // Thêm một số sinh viên mẫu để test
        addStudent("Nguyen Van A")
        addStudent("Nguyen Thi B")
        addStudent("Nguyen Van C")
    }

    // Thêm sách mới
    fun addBook(title: String) {
        val newBook = Book(
            id = (books.size + 1).toString(),
            title = title
        )
        _books.add(newBook)
    }

    // Xóa sách
    fun removeBook(book: Book) {
        _books.remove(book)
    }

    // Thêm sinh viên
    fun addStudent(name: String) {
        val newStudent = Student(
            id = (students.size + 1).toString(),
            name = name
        )
        _students.add(newStudent)
    }

    // Xóa sinh viên
    fun removeStudent(student: Student) {
        _students.remove(student)
    }

    // Mượn sách cho sinh viên
    fun borrowBook(student: Student, book: Book): Boolean {
        if (!book.isBorrowed) {
            // Tìm và cập nhật book trong danh sách chính
            val bookIndex = _books.indexOfFirst { it.id == book.id }
            if (bookIndex != -1) {
                book.isBorrowed = true
                student.borrowedBooks.add(book)
                // Force recomposition
                _updateTrigger.value++
                return true
            }
        }
        return false
    }

    // Trả sách
    fun returnBook(student: Student, book: Book) {
        book.isBorrowed = false
        student.borrowedBooks.remove(book)
        // Force recomposition
        _updateTrigger.value++
    }

    // Tìm sinh viên theo tên (exact match)
    fun findStudentByName(name: String): Student? {
        return students.find { it.name.equals(name, ignoreCase = true) }
    }

    // Tìm sinh viên gợi ý theo phần tên
    fun searchStudents(query: String): List<Student> {
        if (query.isBlank()) return students
        return students.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }
}