package com.unirfp.dmr_actividad1_pmdm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    var salarioBruto by mutableStateOf("")
        private set
    var numPagas by mutableStateOf("")
        private set
    var edad by mutableStateOf("")
        private set
    var grupoProfesional by mutableStateOf("")
        private set
    var gradoDiscapacidad by mutableStateOf("")
        private set
    var estadoCivil by mutableStateOf("")
        private set
    var numHijos by mutableStateOf("")
        private set

    var salarioNetoResult by mutableStateOf(0.0)
        private set
    var retencionIRPFResult by mutableStateOf(0.0)
        private set
    var deduccionesResult by mutableStateOf(0.0)
        private set

    fun updateSalarioBruto(v: String) { salarioBruto = v }
    fun updateNumPagas(v: String) { numPagas = v }
    fun updateEdad(v: String) { edad = v }
    fun updateGrupoProfesional(v: String) { grupoProfesional = v }
    fun updateGradoDiscapacidad(v: String) { gradoDiscapacidad = v }
    fun updateEstadoCivil(v: String) { estadoCivil = v }
    fun updateNumHijos(v: String) { numHijos = v }

    fun calculateAndSaveResults() {
        val sBruto = salarioBruto.toDoubleOrNull() ?: 0.0
        val edadVal = edad.toIntOrNull() ?: 0
        val gDiscapacidad = gradoDiscapacidad.toDoubleOrNull() ?: 0.0
        val hijos = numHijos.toIntOrNull() ?: 0

        var irpf = sBruto * 0.15
        if (edadVal < 30) irpf *= 0.9

        var deducciones = sBruto * 0.02
        deducciones += hijos * 300.0
        deducciones += gDiscapacidad * 10.0

        val sNeto = sBruto - irpf + deducciones

        salarioNetoResult = sNeto
        retencionIRPFResult = irpf
        deduccionesResult = deducciones
    }
}