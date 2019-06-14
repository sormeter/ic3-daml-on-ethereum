package com.daml.ethereum

import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService

object EthPublisher extends App {

  val web3 =
    Web3j.build(new HttpService("https://mainnet.infura.io/v3/533d2271072844c38be956e2093d8577"))
  val web3ClientVersion = web3.web3ClientVersion().send()
  val clientVersion = web3ClientVersion.getWeb3ClientVersion
  println(clientVersion)
}
