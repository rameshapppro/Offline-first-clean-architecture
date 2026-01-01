package com.ramesh.sample.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ramesh.sample.domain.model.Movie
import com.ramesh.sample.ui.viewmodel.MovieViewModel
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

// --- MAIN SCREEN COMPOSABLE ---

@Composable
fun UserScreen(movieViewModel: MovieViewModel = hiltViewModel()) {
    val uiState by movieViewModel.state.collectAsState()
    val movies = uiState.movies

    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                NowShowingSection(movies = movies)
            }
            item {
                UpcomingSection(movies = movies)
            }
            item {
                CollectionsSection(movies = movies.reversed())
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// --- SCREEN SECTIONS ---

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NowShowingSection(movies: List<Movie>) {
    if (movies.isEmpty()) return

    val pagerState = rememberPagerState(
        initialPage = movies.size / 2,
        pageCount = { movies.size }
    )

    // Auto-scroll logic
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000) // 3-second delay between scrolls
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Now Showing",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.White,
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 64.dp),
        ) { page ->
            val movie = movies.getOrNull(page) ?: return@HorizontalPager
            val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
            val scale = lerp(
                start = 0.8f,
                stop = 1f,
                fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
            )

            MovieCard(
                movie = movie,
                modifier = Modifier.graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
            )
        }
    }
}

@Composable
private fun UpcomingSection(movies: List<Movie>) {
    Column {
        SectionTitle(title = "Upcoming", onViewAllClicked = { /* TODO */ })
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(movies) { movie ->
                MoviePosterCard(movie = movie)
            }
        }
    }
}

@Composable
private fun CollectionsSection(movies: List<Movie>) {
    Column {
        SectionTitle(title = "Collections", onViewAllClicked = { /* TODO */ })
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(movies) { movie ->
                CollectionCard(movie = movie)
            }
        }
    }
}

// --- REUSABLE COMPONENTS ---

@Composable
private fun MovieCard(movie: Movie, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .width(250.dp)
            .height(350.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(movie.poster).crossfade(true).build(),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun MoviePosterCard(movie: Movie) {
    Column(
        modifier = Modifier.width(140.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable { /* TODO */ },
            shape = RoundedCornerShape(16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(movie.poster).crossfade(true).build(),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.title ?: "",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun CollectionCard(movie: Movie) {
    Column(
        modifier = Modifier.width(140.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clickable { /* TODO */ },
            shape = RoundedCornerShape(24.dp),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(movie.poster).crossfade(true).build(),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        movie.year?.let {
            Text(
                text = it,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun SectionTitle(title: String, onViewAllClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "View All",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.clickable(onClick = onViewAllClicked)
        )
    }
}
