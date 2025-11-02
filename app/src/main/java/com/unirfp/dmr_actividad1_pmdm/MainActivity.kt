package com.unirfp.dmr_actividad1_pmdm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.unirfp.dmr_actividad1_pmdm.ui.theme.DMR_Actividad1_PMDMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DMR_Actividad1_PMDMTheme {
                val navController = rememberNavController()
                val vm: CalculatorViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = "input_screen"
                ) {
                    composable("input_screen") {
                        DataInputScreen(navController = navController, viewModel = vm)
                    }
                    composable("results_screen") {
                        ResultsScreen(viewModel = vm)
                    }
                }
            }
        }
    }
}

@Composable
fun DataInputScreen(
    navController: NavController,
    viewModel: CalculatorViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 40.dp)
    ) {
        Text(
            text = "Datos para Cálculo de Salario Neto",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = viewModel.salarioBruto,
            onValueChange = viewModel::updateSalarioBruto,
            label = { Text("1. Salario bruto anual (€)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = viewModel.numPagas,
            onValueChange = viewModel::updateNumPagas,
            label = { Text("2. Número de pagas") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = viewModel.edad,
            onValueChange = viewModel::updateEdad,
            label = { Text("3. Edad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = viewModel.grupoProfesional,
            onValueChange = viewModel::updateGrupoProfesional,
            label = { Text("4. Grupo Profesional (Texto libre)") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = viewModel.gradoDiscapacidad,
            onValueChange = viewModel::updateGradoDiscapacidad,
            label = { Text("5. Grado de discapacidad (%)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = viewModel.estadoCivil,
            onValueChange = viewModel::updateEstadoCivil,
            label = { Text("6. Estado civil (ej. Soltero/Casado)") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = viewModel.numHijos,
            onValueChange = viewModel::updateNumHijos,
            label = { Text("7. Número de hijos") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                viewModel.calculateAndSaveResults()
                navController.navigate("results_screen")
            }),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        Spacer(Modifier.padding(top = 24.dp))

        Button(
            onClick = {
                viewModel.calculateAndSaveResults()
                navController.navigate("results_screen")
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Calcular Salario Neto") }
    }
}

@Composable
fun ResultsScreen(viewModel: CalculatorViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 40.dp)
    ) {
        Text(
            text = "Resultados de la Nómina",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        ResultRow(label = "Salario Bruto Anual:", value = "${viewModel.salarioBruto} €")
        ResultRow(label = "Retención de IRPF:", value = "%.2f €".format(viewModel.retencionIRPFResult))
        ResultRow(label = "Posibles deducciones:", value = "%.2f €".format(viewModel.deduccionesResult))

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        ResultRow(
            label = "SALARIO NETO ESTIMADO:",
            value = "%.2f €".format(viewModel.salarioNetoResult),
            isHighlight = true
        )
    }
}

@Composable
fun ResultRow(label: String, value: String, isHighlight: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = if (isHighlight) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyLarge,
            color = if (isHighlight) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = value,
            style = if (isHighlight) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyLarge,
            color = if (isHighlight) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}