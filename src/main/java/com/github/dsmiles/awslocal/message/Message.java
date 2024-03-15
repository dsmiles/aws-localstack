package com.github.dsmiles.awslocal.message;

import java.util.UUID;

public record Message(UUID uuid, String content) {}
