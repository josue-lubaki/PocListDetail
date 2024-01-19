package ca.josue_lubaki.poclistdetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.AnimatedPane
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.josue_lubaki.poclistdetail.model.Car
import ca.josue_lubaki.poclistdetail.ui.theme.PocListDetailTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PocListDetailTheme {
                var selectedCar : Car? by rememberSaveable(stateSaver = Car.Saver) {
                    mutableStateOf(null)
                }

                val navigator = rememberListDetailPaneScaffoldNavigator()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Content(
                        selectedCar = selectedCar,
                        onSelectedCarChange = { car : Car ->
                            selectedCar = car
                        },
                        navigator = navigator
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun Content(
    selectedCar : Car?,
    onSelectedCarChange : (Car) -> Unit,
    navigator : ThreePaneScaffoldNavigator
) {
    ListDetailPaneScaffold(
        scaffoldState = navigator.scaffoldState,
        listPane = {
            AnimatedPane(
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.primary
                )) {
                CarList { car ->
                    onSelectedCarChange(car)
                    navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                }
            }
        },
        detailPane = {
            AnimatedPane(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.secondary
                    )
            ) {
                Box(contentAlignment = Alignment.Center){
                    if (selectedCar == null) {
                        Text(
                            text = "Select a car",
                            modifier = Modifier
                                .padding(16.dp)
                            ,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    } else {
                        CarDetails(car = selectedCar)
                    }
                }
            }
        }
    )
    
}

@Composable
private fun CarList(
    onClick : (Car) -> Unit
) {
    LazyColumn {
        items(10) { index ->
            Text(
                text = "Car number #$index",
                modifier = Modifier
                    .clickable { onClick(Car(index)) }
                    .fillMaxWidth()
                    .padding(16.dp)
                ,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun CarDetails(
    car : Car,
) {
    Text(
        text = "Car number #${car.id}",
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onSecondary,
    )
}



@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=1280dp,height=900dp,dpi=320,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
private fun GreetingPreview() {
    PocListDetailTheme {
        Content(
            selectedCar = null,
            onSelectedCarChange = { _ : Car -> },
            navigator = rememberListDetailPaneScaffoldNavigator()
        )
    }
}