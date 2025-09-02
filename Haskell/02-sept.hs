data AEB a = Hoja a | Bin (AEB a) a (AEB a) deriving Show

foldAEB :: (b -> a -> b -> b) -> (a -> b) -> AEB a -> b
foldAEB _ f' (Hoja a) = f' a
foldAEB f f' (Bin l n r) = f (foldAEB f f' l) n (foldAEB f f' r)

alturaAEB :: AEB a -> Int
alturaAEB = foldAEB (\l _ r -> 1 + (max l r)) (const 1)

espejo :: AEB a -> AEB a
espejo = foldAEB (\l n r -> Bin r n l) Hoja



