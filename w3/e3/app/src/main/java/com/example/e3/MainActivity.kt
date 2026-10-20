package com.example.e3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import coil3.compose.AsyncImage
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.OutlinedTextField
// import androidx.navigation.compose.NavHost
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue // if it is a 'var' instead of a 'val'
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e3.ui.theme.E3Theme

data class Message(
    val content: String,
    val timestamp: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            E3Theme {
                myHome()
            }
        }
    }
}

@Composable
fun myImage(innerPadding: PaddingValues) {
    val painter = painterResource(id = R.drawable.gundam)
    val link =
        "https://gamek.mediacdn.vn/thumb_w/550/133514250583805952/2026/9/12/gundam-zero-xarx-0-drawing-saber-1789223667520-1789223667756879143635.webp"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = "Sample Image",
            )

        }
        Hyperlink(Modifier, link)
        Surface(
            modifier = Modifier
                .padding(10.dp)
        ) {
            AsyncImage(
                model = link,
                contentDescription = "Translated description of what the image contains"
            )
        }

    }
}

@Composable
fun myTextField(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            state = rememberTextFieldState(),
            placeholder = { Text("Thông tin nhập") })

    }
}

@Composable
fun uiComponentList(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display
        title("Display")
        myCard("Text", "Display Text")
        myCard("Image", "Display an image")
        //input
        title("Input")
        myCard("TextField", "Input field for test")
        myCard("PasswordField", "Input field for password")
        //layout
        title("Layout")
        myCard("Column", "Arranges elements vertically")
        myCard("Row", "Arranges elements Horiznotally")
    }
}

@Composable
fun rowLayout(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        repeat(4) {
            layoutRow()
        }
    }
}

@Composable
fun layoutRow() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant   
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            colorBox(Color(0xFF9DB8F2), Modifier.weight(1f))
            colorBox(Color(0xFF4A7FE8), Modifier.weight(1f))
            colorBox(Color(0xFF9DB8F2), Modifier.weight(1f))
        }
    }
}

@Composable
fun colorBox(color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(50.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
    )
}

@Composable
fun textDetail(innerPadding: PaddingValues) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {
        Surface(
            modifier = Modifier
                .padding(10.dp)
        )
        {
            Text(
                buildAnnotatedString {
                    withStyle(style = ParagraphStyle(lineHeight = 60.sp)) {
                        withStyle(style = SpanStyle()) {
                            append("The ")
                        }
                        withStyle(
                            style = SpanStyle(
                                textDecoration = TextDecoration.LineThrough
                            )
                        ) {
                            append("Quick ")
                        }

                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold, color = Color.Yellow
                            )
                        ) {
                            append(" Yellow")
                        }
                        append(" Fox")
                        withStyle(
                            style = SpanStyle(
                                letterSpacing = 10.0.sp
                            )
                        ) {
                            append(" jumps")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic
                            )
                        ) {
                            append(" over ")

                        }
                        withStyle(
                            style = SpanStyle(
                                textDecoration = TextDecoration.LineThrough
                            )
                        ) {
                            append("the")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontFamily.Cursive,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold

                            )
                        ) {
                            append(" lazy")
                        }

                        append(" dog.")


                    }
                },
                fontSize = 60.sp,

            )
        }

    }
}


@Composable
fun myHome() {
    var currentPage by rememberSaveable { mutableIntStateOf(1) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            navigationTopBar(currentPage, onPageChange = { newPage -> currentPage = newPage })
        }
    ) { innerPadding ->
        if (currentPage == 0) {
            uiComponentList(innerPadding)
        } else if (currentPage == 1) {
            textDetail(innerPadding)
        } else if (currentPage == 2) {
            myImage(innerPadding)
        } else if (currentPage == 3) {
            myTextField(innerPadding)
        } else if (currentPage == 4) {
            rowLayout(innerPadding)
        }
    }

}


@Composable
fun navigationTopBar(currentPage: Int, onPageChange: (Int) -> Unit) {
    val listOfPages = listOf(
        "UI Component List",
        "Text Detail",
        "Images",
        "TextField",
        "Row Layout"
    )
    myTopAppBar(currentPage = currentPage, pageList = listOfPages, onPageChange)
}

@Composable
fun myTopAppBar(currentPage: Int, pageList: List<String>, onPageChange: (Int) -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .statusBarsPadding()
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp),

            ) {
                if (currentPage != 0) {
                    IconButton(
                        onClick = { onPageChange(currentPage - 1) }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )

                    }

                }
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    pageList[currentPage],
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))
                if (currentPage != pageList.size - 1) {
                    IconButton(
                        onClick = { onPageChange(currentPage + 1) }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )

                    }


                }
            }
        }
    }
}


@Composable
fun myCard(title: String, desp: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(title, fontWeight = FontWeight.Bold)
            Text(desp)
        }
    }
}


@Composable
fun title(title: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {

        Text(title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
}

@Composable
fun Hyperlink(
    modifier: Modifier = Modifier,
    link: String
) {
    var hyperl = """
  <center>
    <a href=$link>Image Link</a>
  </center>
    """.trimIndent()

    Text(
        AnnotatedString.fromHtml(
            hyperl,
            linkStyles = TextLinkStyles(
                style = SpanStyle(
                    textDecoration = TextDecoration.Underline,
                    fontStyle = FontStyle.Italic,
                    color = Color.Blue
                )
            )
        ),
        modifier
    )
}
