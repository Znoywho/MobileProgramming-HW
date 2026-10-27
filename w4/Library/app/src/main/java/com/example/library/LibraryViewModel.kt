package com.example.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class Book(val id: Int, val title: String, val author: String)

data class Student(val id: Int, val name: String)

// Giữ toàn bộ dữ liệu thư viện, dùng chung cho cả 3 tab
class LibraryViewModel : ViewModel() {
    private var nextBookId = 1
    private var nextStudentId = 1

    val books = mutableStateListOf<Book>()
    val students = mutableStateListOf<Student>()

    // bookId -> studentId: mỗi quyển chỉ được một sinh viên mượn
    private val borrows = mutableStateMapOf<Int, Int>()

    var currentStudentId by mutableStateOf<Int?>(null)
        private set

    init {
        listOf("Nguyen Van A", "Nguyen Thi B", "Tran Van C").forEach(::addStudent)
        listOf(
            "Sách 01" to "Tác giả 01",
            "Sách 02" to "Tác giả 02",
            "Sách 03" to "Tác giả 03",
            "Sách 04" to "Tác giả 04",
            "Sách 05" to "Tác giả 05"
        ).forEach { (title, author) -> addBook(title, author) }
        currentStudentId = students.first().id
        borrow(students.first().id, listOf(books[0].id, books[1].id))
    }

    val currentStudent: Student?
        get() = students.find { it.id == currentStudentId }

    fun booksOf(studentId: Int): List<Book> = books.filter { borrows[it.id] == studentId }

    fun availableBooks(): List<Book> = books.filter { it.id !in borrows }

    fun borrowerOf(bookId: Int): Student? = borrows[bookId]?.let { id -> students.find { it.id == id } }

    fun selectStudent(id: Int) {
        currentStudentId = id
    }

    fun addBook(title: String, author: String) {
        books.add(Book(nextBookId++, title.trim(), author.trim()))
    }

    fun removeBook(id: Int) {
        books.removeAll { it.id == id }
        borrows.remove(id)
    }

    fun addStudent(name: String) {
        students.add(Student(nextStudentId++, name.trim()))
    }

    fun removeStudent(id: Int) {
        students.removeAll { it.id == id }
        borrows.filterValues { it == id }.keys.forEach(borrows::remove)
        if (currentStudentId == id) currentStudentId = students.firstOrNull()?.id
    }

    fun borrow(studentId: Int, bookIds: Collection<Int>) {
        bookIds.filter { it !in borrows }.forEach { borrows[it] = studentId }
    }

    fun returnBook(bookId: Int) {
        borrows.remove(bookId)
    }
}
