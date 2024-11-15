package com.companyname.learningcompose

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.companyname.learningcompose.ui.theme.LearningComposeTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.URL

@Preview(showBackground = true)
@Composable
fun Design1Preview() {
    LearningComposeTheme {
        MainScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { BarText("Terminal Yazılım Şefliği") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary, // Arka plan rengi
                    titleContentColor = MaterialTheme.colorScheme.onPrimary // Başlık ve içerik rengi
                )
            )
        },
        bottomBar = {
            BottomAppBar {
                BarText("Hack is on the way")
            }
        },
        content = { innerPadding ->
//            TwoColumnTable(innerPadding)
//            ListsOnRows(innerPadding)
//            SubDesign2(innerPadding)
//            SubDesign1(innerPadding)

//            UserProfileList(innerPadding)
//            ProductList(innerPadding)
//            TodoList(innerPadding)
//            ProgressBarExample(innerPadding, 0.6f)
            LoginScreen(innerPadding){ username, password -> println("Username: $username, Password: $password") }
//        MessageCard(innerPadding)
        }
    )
}

@Composable
private fun BarText(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = text)
    }
}

/*
* WhatsApp like message card
* */
@Composable
fun MessageCard(innerPadding: PaddingValues, name: String = "Dear Selçuk") {
    MaterialTheme { // Use MaterialTheme from Material 3
        Surface(
            modifier = Modifier.padding(innerPadding).padding(8.dp),
            color = Color(0xFFEDEDED),
            shape = RoundedCornerShape(8.dp),
            tonalElevation = 16.dp, // Now elevation should be recognized
            shadowElevation = 16.dp // And shadowElevation should be recognized
        ) {
            Text(
                text = "Hello, $name!",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun UserProfileList(innerPadding: PaddingValues) {

    data class UserProfile(val name: String, val email: String, val profileImageUrl: String)

    // fake data
    val exampleUsers = listOf(
        UserProfile("Ali Yılmaz", "john.quincy.adams@examplepetstore.com", "https://example.com/ali.jpg"),
        UserProfile("Ayşe Demir", "james.s.sherman@example-pet-store.com", "https://example.com/ayse.jpg"),
        UserProfile("Mehmet Kara", "william.henry.harrison@example-pet-store.com", "https://example.com/mehmet.jpg"),
        UserProfile("Elif Ak", "john.mckinley@examplepetstore.com", "https://example.com/elif.jpg")
    )

    @Composable
    fun LoadImage(imageURl: String): Painter {
        var image by remember { mutableStateOf<ImageBitmap?>(null) }
        var painter by remember { mutableStateOf<Painter?>(null) }
        LaunchedEffect(imageURl) {
            withContext(Dispatchers.IO) {
                try {
                    val url = URL(imageURl)
                    val connection = url.openStream()
                    image = BitmapFactory.decodeStream(connection).asImageBitmap()
                    painter = BitmapPainter(image!!)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        return painter ?: rememberVectorPainter(Icons.Filled.AccountCircle)
    }

    @Composable
    fun UserProfileItem(profile: UserProfile){
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp).background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // image
            Image(
                // coil kütüphanesi ile resim yükleme işlemi. çok sayıda optimizasyon var!
//                painter = rememberAsyncImagePainter(profile.profileImageUrl),
                // coil kütüphanesi yerine kullanılacak resim yükleme işlemi
                painter = LoadImage(profile.profileImageUrl),
                contentDescription = "Profile Image",
                modifier = Modifier.size(60.dp).clip(shape = CircleShape).padding(end = 16.dp).clickable { println("Profile clicked: ${profile.name}") }
            )
            // name
            Spacer(modifier = Modifier.width(16.dp))
            // email
            Column {
                Text(text = profile.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = profile.email, style = MaterialTheme.typography.bodySmall)
            }
        }
        HorizontalDivider()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)
    ) {
        items(exampleUsers) { profile ->
            UserProfileItem(profile = profile)
        }
    }
}

@Composable
fun ProductList(innerPadding: PaddingValues) {

    data class Product(val name: String, val description: String, val price: Double)

    val products = listOf(
        Product("Telefon", "Elektronik", 10000.0),
        Product("Laptop", "Bilgisayar", 15000.0),
        Product("Kulaklık", "Aksesuar", 500.0),
        Product("Monitör", "Elektronik", 2500.0)
    )
    @Composable
    fun ProductListItem(product: Product){
        Card(
            modifier = Modifier.fillMaxWidth().padding(8.dp).clickable { println("Product clicked: ${product.name}") },
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = product.name, style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = product.description, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Fiyat: \$${product.price}", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)
    ) {
        items(products) { product ->
            ProductListItem(product = product)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}

@Composable
fun TodoList(innerPadding: PaddingValues) {
    // TodoItem veri modelini dışarıya alıyoruz, böylece gerektiğinde güncellenebilir
    data class TodoItem(val title: String, val description: String, var isCompleted: Boolean)

    // Başlangıçtaki görev listesi
    val tasks = remember { mutableStateListOf(
        TodoItem("Kotlin Öğren", "Kotlin ile geliştirme", true),
        TodoItem("Proje Bitir", "Projeyi bitirme", false),
        TodoItem("Egzersiz Yap", "Egzersiz yapma", false),
        TodoItem("Kitap Oku", "Kitap okuma", true),
        TodoItem("Çamaşır Yıka", "Çamaşır Yıkama", false),
        TodoItem("Ütü yap", "Ütü yapma", false),
        TodoItem("Evi süpür", "Evi süpürma", true),
        TodoItem("Yemek yap", "Yemek yapma", false),
        TodoItem("Çöpü çıkar", "Çöpü çıkarma", true)
    )
    }

    // Her bir todo item'ı görüntüleyecek Row bileşeni
    @Composable
    fun TodoListRow(task: TodoItem, onCheckedChange: (Boolean) -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { onCheckedChange(!task.isCompleted) }, // CheckBox'ın durumu tıklanarak değiştirilecek
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onCheckedChange(it) }, // Değişiklik işlevi
                modifier = Modifier.padding(end = 16.dp)
            )
            Text(text = task.title, style = MaterialTheme.typography.bodyLarge)
        }
        HorizontalDivider(color = Color.LightGray, thickness = 4.dp)
    }

    // LazyColumn ile listeyi render ediyoruz
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)
    ) {
        items(tasks) { task ->
            // task bir kopyadır! değiştirilen bir özellik ana verilere yansılmaz!
            TodoListRow(task = task) { isChecked ->
                // Listede bulunan `task` öğesinin tamamlanma durumunu güncelliyoruz
                val index = tasks.indexOf(task)
                if (index != -1) {
                    tasks[index] = task.copy(isCompleted = isChecked) // Yeni bir kopya oluşturup güncelliyoruz
                    println("TODO: ${task.title} completed: ${task.isCompleted}")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ProgressBarExample(innerPadding: PaddingValues, percentage: Float = 0.6f){
    var progress  by remember { mutableFloatStateOf(percentage) }

    @Composable
    fun ProgressBar1(percentage : Float){
        Box(modifier = Modifier.fillMaxWidth().height(20.dp).background(Color.Gray.copy(alpha = 0.3f))){
            Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(percentage).background(Color.Green))
        }
    }
    @Composable
    fun ProgressBar2(percentage: Float) {
        LinearProgressIndicator(
            progress = { percentage },
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
        )
    }

    // Simulating a loading process
    LaunchedEffect(Unit) {
        while (progress < 1f) {
            delay(100) // Her 100 ms'de bir progress'i artır
            progress += 0.01f // Yüzdeyi artır
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Loading : ${String.format("%.2f", if(progress*100 > 100) 100f else (progress*100))}%")
        Spacer(modifier = Modifier.height(8.dp))
        ProgressBar1(progress)
        Spacer(modifier = Modifier.height(16.dp))
        ProgressBar2(progress)
    }

}

@Composable
fun LoginScreen(innerPadding: PaddingValues, onLogin: (String, String) -> Unit) {
    // State'leri yönetmek için remember kullanımı
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // Ana yapı
    Column(
        modifier = Modifier
            .fillMaxSize().padding(innerPadding)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Başlık
        Text(
            text = "Giriş Yap",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Kullanıcı Adı Girişi
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Kullanıcı Adı") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            singleLine = true
        )

        // Şifre Girişi
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Şifre") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        // Yükleniyor göstergesi
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(48.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Giriş Butonu
        Button(
            onClick = {
                // Yükleniyor durumu başlat
                isLoading = true
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            enabled = !isLoading // Yükleniyor durumunda buton devre dışı bırakılır
        ) {
            Text("Giriş Yap")
        }

        if (isLoading) {
            LaunchedEffect(Unit) {
                // Simulasyonlu bir giriş işlemi
                delay(2000)
                // Giriş işlemi simülasyonu (API çağrısı gibi)
                onLogin(username, password)
                // Yükleniyor durumu durdurulacak
                isLoading = false
            }
        }
    }
}

@Composable
fun TwoColumnTable(innerPadding: PaddingValues) {
    val exampleData = mapOf(
        "Ad" to "Ali",
        "Soyad" to "Yılmaz",
        "Yaş" to "30",
        "Şehir" to "İstanbul",
        "Meslek" to "Yazılım Geliştirici",
        "Hobiler" to "Kitap Okuma"
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(innerPadding)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        exampleData.forEach { key, value ->
            item {
                Row(
                    modifier = Modifier.fillMaxSize().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "- $key",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = value,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                }
        }
    }
}

@Composable
fun ListsOnRows(innerPadding: PaddingValues){
    val myList = listOf("Selçuk", "Ali", "Veli")
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        for (name in myList) {
            Row(
                modifier = Modifier.padding(innerPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Greeting(name = name)
                Greeting(name = name)
            }
        }
    }
}

@Composable
fun SubDesign2(innerPadding: PaddingValues){
    var text by remember { mutableStateOf("Merhaba Compose!") }
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Button(onClick = { text = "Bye Bye" }) {
            Text(text = "Clieck Me")
        }
        Text(text = text, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun SubDesign1(innerPadding: PaddingValues) {
    val name = "Selçuk"
    Column {
        Greeting(
            name = name,
            modifier = Modifier.padding(innerPadding)
        )
        Welcome(
            name = name,
            modifier = Modifier.padding(innerPadding)
        )
        Counter()
    }
}

@Composable
fun Counter() {
    var counter by remember { mutableIntStateOf(0) }
    Button(onClick = { counter++ }) {
        Text(text = "Click Me $counter")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun Welcome(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Welcome $name!",
        modifier = modifier
    )
}