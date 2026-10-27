package com.example.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.library.ui.theme.LibBlue
import com.example.library.ui.theme.LibRed
import com.example.library.ui.theme.TextSecondary

// Khung chung cho 2 tab danh sách: tiêu đề, danh sách và nút + ở góc
@Composable
private fun ListScreen(
    title: String,
    emptyText: String,
    isEmpty: Boolean,
    onAdd: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            ScreenTitle(title)
            if (isEmpty) Text(emptyText, color = TextSecondary) else content()
        }
        FloatingActionButton(
            onClick = onAdd,
            containerColor = LibBlue,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Thêm")
        }
    }
}

// Tab DS Sách: tạo danh sách sách, xem thông tin và ai đang mượn
@Composable
fun BooksScreen(viewModel: LibraryViewModel) {
    var showAdd by remember { mutableStateOf(false) }
    var bookDetail by remember { mutableStateOf<Book?>(null) }

    ListScreen(
        title = "Danh sách sách",
        emptyText = "Chưa có sách nào.",
        isEmpty = viewModel.books.isEmpty(),
        onAdd = { showAdd = true }
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(viewModel.books, key = { it.id }) { book ->
                val borrower = viewModel.borrowerOf(book.id)
                ItemCard(onClick = { bookDetail = book }) {
                    Icon(Icons.Outlined.MenuBook, contentDescription = null, tint = LibBlue)
                    Column(modifier = Modifier.weight(1f)) {
                        Text(book.title, fontWeight = FontWeight.Medium)
                        Text(book.author, color = TextSecondary)
                        Text(
                            borrower?.let { "Đang mượn: ${it.name}" } ?: "Còn trong thư viện",
                            color = if (borrower != null) LibRed else TextSecondary
                        )
                    }
                    IconButton(onClick = { viewModel.removeBook(book.id) }) {
                        Icon(Icons.Outlined.Delete, contentDescription = "Xoá sách")
                    }
                }
            }
        }
    }

    if (showAdd) {
        InputDialog(
            title = "Thêm sách",
            fields = listOf("Tên sách", "Tác giả"),
            onDismiss = { showAdd = false },
            onConfirm = { (title, author) ->
                viewModel.addBook(title, author)
                showAdd = false
            }
        )
    }

    bookDetail?.let { book ->
        BookDetailDialog(book, viewModel.borrowerOf(book.id), onDismiss = { bookDetail = null })
    }
}

// Tab Sinh viên: tạo danh sách sinh viên, xem số sách mỗi người đang mượn
@Composable
fun StudentsScreen(viewModel: LibraryViewModel) {
    var showAdd by remember { mutableStateOf(false) }

    ListScreen(
        title = "Danh sách sinh viên",
        emptyText = "Chưa có sinh viên nào.",
        isEmpty = viewModel.students.isEmpty(),
        onAdd = { showAdd = true }
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(viewModel.students, key = { it.id }) { student ->
                val books = viewModel.booksOf(student.id)
                ItemCard(onClick = { viewModel.selectStudent(student.id) }) {
                    Icon(Icons.Outlined.Person, contentDescription = null, tint = LibBlue)
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            student.name,
                            fontWeight = if (student.id == viewModel.currentStudentId) FontWeight.Bold else FontWeight.Medium
                        )
                        Text(
                            if (books.isEmpty()) "Chưa mượn sách"
                            else "Đang mượn ${books.size}: " + books.joinToString { it.title },
                            color = TextSecondary
                        )
                    }
                    IconButton(onClick = { viewModel.removeStudent(student.id) }) {
                        Icon(Icons.Outlined.Delete, contentDescription = "Xoá sinh viên")
                    }
                }
            }
        }
    }

    if (showAdd) {
        InputDialog(
            title = "Thêm sinh viên",
            fields = listOf("Họ tên"),
            onDismiss = { showAdd = false },
            onConfirm = { (name) ->
                viewModel.addStudent(name)
                showAdd = false
            }
        )
    }
}
