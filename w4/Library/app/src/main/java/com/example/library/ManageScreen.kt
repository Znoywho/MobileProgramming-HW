package com.example.library

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.library.ui.theme.LibGreyBox
import com.example.library.ui.theme.LibRed
import com.example.library.ui.theme.TextSecondary

// Tab Quản lý: chọn sinh viên, xem/trả sách đang mượn, thêm sách để mượn
@Composable
fun ManageScreen(viewModel: LibraryViewModel) {
    val student = viewModel.currentStudent
    val borrowed = student?.let { viewModel.booksOf(it.id) }.orEmpty()

    var showStudentPicker by remember { mutableStateOf(false) }
    var showBorrowDialog by remember { mutableStateOf(false) }
    var bookDetail by remember { mutableStateOf<Book?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        ScreenTitle("Hệ thống\nQuản lý Thư viện")

        SectionLabel("Sinh viên")
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = student?.name ?: "Chưa có sinh viên",
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(12.dp))
            BlueButton("Thay đổi", onClick = { showStudentPicker = true })
        }

        Spacer(Modifier.height(24.dp))
        SectionLabel("Danh sách sách")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(LibGreyBox, RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            if (borrowed.isEmpty()) {
                Text(
                    "Bạn chưa mượn quyển sách nào\nNhấn 'Thêm' để bắt đầu hành trình đọc sách!",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    color = TextSecondary
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(borrowed, key = { it.id }) { book ->
                        ItemCard(onClick = { bookDetail = book }) {
                            // Bỏ tick = trả sách
                            Checkbox(
                                checked = true,
                                onCheckedChange = { viewModel.returnBook(book.id) },
                                colors = CheckboxDefaults.colors(checkedColor = LibRed)
                            )
                            Text(book.title)
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        BlueButton(
            "Thêm",
            onClick = { showBorrowDialog = true },
            enabled = student != null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(200.dp)
        )
        Spacer(Modifier.height(16.dp))
    }

    if (showStudentPicker) {
        StudentPickerDialog(
            students = viewModel.students,
            selectedId = viewModel.currentStudentId,
            onDismiss = { showStudentPicker = false },
            onSelect = {
                viewModel.selectStudent(it)
                showStudentPicker = false
            }
        )
    }

    if (showBorrowDialog && student != null) {
        BorrowDialog(
            available = viewModel.availableBooks(),
            onDismiss = { showBorrowDialog = false },
            onConfirm = {
                viewModel.borrow(student.id, it)
                showBorrowDialog = false
            }
        )
    }

    bookDetail?.let { book ->
        BookDetailDialog(
            book = book,
            borrower = viewModel.borrowerOf(book.id),
            onDismiss = { bookDetail = null }
        )
    }
}

@Composable
private fun StudentPickerDialog(
    students: List<Student>,
    selectedId: Int?,
    onDismiss: () -> Unit,
    onSelect: (Int) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Chọn sinh viên") },
        text = {
            if (students.isEmpty()) {
                Text("Chưa có sinh viên. Thêm ở tab Sinh viên.")
            } else {
                LazyColumn {
                    items(students, key = { it.id }) { s ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelect(s.id) },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = s.id == selectedId, onClick = { onSelect(s.id) })
                            Text(s.name)
                        }
                    }
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Đóng") } }
    )
}

// Chọn nhiều sách còn trong thư viện để mượn
@Composable
private fun BorrowDialog(
    available: List<Book>,
    onDismiss: () -> Unit,
    onConfirm: (Set<Int>) -> Unit
) {
    var selected by remember { mutableStateOf(setOf<Int>()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Mượn sách") },
        text = {
            if (available.isEmpty()) {
                Text("Thư viện đã hết sách để mượn.")
            } else {
                LazyColumn {
                    items(available, key = { it.id }) { book ->
                        val checked = book.id in selected
                        val toggle = { selected = if (checked) selected - book.id else selected + book.id }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = toggle),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = checked,
                                onCheckedChange = { toggle() },
                                colors = CheckboxDefaults.colors(checkedColor = LibRed)
                            )
                            Column {
                                Text(book.title, fontWeight = FontWeight.Medium)
                                Text(book.author, color = TextSecondary)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(selected) }, enabled = selected.isNotEmpty()) {
                Text("Mượn")
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Huỷ") } }
    )
}

@Composable
fun BookDetailDialog(book: Book, borrower: Student?, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(book.title) },
        text = {
            Column {
                Text("Mã sách: ${book.id}")
                Text("Tác giả: ${book.author}")
                Text("Trạng thái: " + (borrower?.let { "Đang được mượn bởi ${it.name}" } ?: "Còn trong thư viện"))
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Đóng") } }
    )
}
