package com.example.semafoare

import androidx.lifecycle.*
import com.example.semafoare.database.TrafficLight
import com.example.semafoare.database.TrafficLightDao
import com.example.semafoare.database.TrafficLightRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrafficLightViewModel(private val repository: TrafficLightRepository): ViewModel() {
    fun allData(): Flow<List<TrafficLight>> = repository.allData
    fun insert(trafficLight: TrafficLight) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            repository.insert(trafficLight)
        }
    }
    fun delete(trafficLight: TrafficLight) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            repository.delete(trafficLight)
        }
    }

    fun update(trafficLight: TrafficLight) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            repository.update(trafficLight)
        }
    }
}


class TrafficLightViewModelFactory(
    private val repository: TrafficLightRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrafficLightViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TrafficLightViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
