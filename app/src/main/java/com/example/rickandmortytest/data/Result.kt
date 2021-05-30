package com.example.rickandmortytest.data

data class Result(val info: Info, val results: List<Character>)

data class Info(val count: Int, val pages: Int, val next: String?, val prev: String?)