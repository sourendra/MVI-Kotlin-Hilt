package com.sourendra.kotlinwithhilt.repository

import com.sourendra.kotlinwithhilt.model.Blog
import com.sourendra.kotlinwithhilt.retrofit.ApiInterface
import com.sourendra.kotlinwithhilt.retrofit.NetworkMapper
import com.sourendra.kotlinwithhilt.room.BlogDao
import com.sourendra.kotlinwithhilt.room.CacheMapper
import com.sourendra.kotlinwithhilt.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import java.lang.Exception
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    private val blogDao: BlogDao,
    private val apiInterface: ApiInterface,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {

    suspend fun getBlog(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
//        delay(1000)
        try {
            val networkBlogs = apiInterface.getBlogs()
            val blogs = networkMapper.mapFromEntityList(networkBlogs)
            for (blog in blogs){
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }
            val cachedBlogs = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))
        }catch (e: Exception){
            emit(DataState.Error(e))
        }
    }
}